# -*- coding: utf-8 -*-

from setuptools import setup, find_packages


with open('README.md') as f:
    readme = f.read()

with open('LICENSE') as f:
    license = f.read()

setup(
    name='sample_tornado_guestbook',
    version='0.1.0',
    description='Sample tornado server REST for guestbook',
    long_description=readme,
    author='Ismael Taboada',
    author_email='ismael.jtr@gmail.com',
    url='https://github.com/rookiesTeam/simple-backend-examples',
    license=license,
    packages=find_packages()
)

